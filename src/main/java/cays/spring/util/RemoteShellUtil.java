package cays.spring.util;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SCPInputStream;
import ch.ethz.ssh2.SCPOutputStream;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Java调用远程脚本
 *
 * @author Chai yansheng
 * @create 2019/9/2
 **/
public class RemoteShellUtil {
    private Connection connection;
    private String ip;
    private String username;
    private String password;
    private static final int TIME_OUT = 3000;
    // 源端
    private static final String OGG_HOME = "/u01/app/oracle/ogg/";
    private static final String GGSCI = OGG_HOME + "ggsci";
    // 目标端
    public static final String BIG_OGG_HOME = "/home/oracle/Desktop/big/ogg/";
    public static final String BIG_GGSCI = BIG_OGG_HOME + "ggsci";

    /**
     * 构造函数
     * @param ip 远程机器ip
     * @param username 远程机器用户名
     * @param password 远程机器密码
     */
    public RemoteShellUtil(String ip, String username, String password) throws IOException {
        this.ip = ip;
        this.username = username;
        this.password = password;
        login();
    }

    /**
     * 登录远程机器
     * @return
     * @throws IOException
     */
    public boolean login() throws IOException {
        connection = new Connection(ip);
        connection.connect();
        return connection.authenticateWithPassword(username, password);
    }
    public Connection getConnection(String ip, String username, String password) throws Exception {
        Connection conn = new Connection(ip);
        conn.connect();
        boolean isLogin = conn.authenticateWithPassword(username, password);
        if (!isLogin) {
            throw new Exception("登录失败");
        }
        return conn;
    }

    /**
     * 通过session执行指令
     * @param session
     * @param command
     * @return
     * @throws Exception
     */
    public int execute(Session session, String command) throws Exception {
        PrintWriter out = new PrintWriter(session.getStdin());
        out.println("cd " + OGG_HOME);
        out.println(command);
        out.println("exit");
        out.close();
        session.waitForCondition(ChannelCondition.CLOSED | ChannelCondition.EOF |
                ChannelCondition.EXIT_STATUS , TIME_OUT);
        printLog(session);
        return session.getExitStatus();
    }

    /**
     * 输出执行信息
     * @param session
     * @throws Exception
     */
    public void printLog(Session session) throws Exception {
        System.out.println("=================================stdout start===============================");
        String stdoutMessage = processStream(new StreamGobbler(session.getStdout()), "utf-8");
        System.out.print(stdoutMessage);
        System.out.println("=================================stdout   end================================");
        System.out.println("Session exit status:" + session.getExitStatus());
        System.out.println("=================================stderr start================================");
        String stderrMessage = processStream(new StreamGobbler(session.getStderr()), "utf-8");
        System.out.print(stderrMessage);
        System.out.println("=================================stderr   end================================");
    }
    /**
     * 建立bash，执行脚本，防止环境变量加载不出来
     * @param command
     * @return
     * @throws Exception
     */
    public int executeBash(String command) {
        int result = -1;
        try {
            if (connection != null || login()) {
                Session session = connection.openSession();
                // 建立虚拟终端
                session.requestPTY("bash");
                // 打开一个Shell
                session.startShell();
                execute(session, command);
                session.close();
            } else {
                throw new Exception("登录远程机器失败" + ip);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return result;
    }
    /**
     * 获取流中数据
     * @param in
     * @param charset
     * @return
     * @throws Exception
     */
    private String processStream(InputStream in, String charset) throws Exception {
        byte[] buf = new byte[1024];
        StringBuilder sb = new StringBuilder();
        while (in.read(buf) != -1) {
            sb.append(new String(buf, charset));
        }
        return sb.toString();
    }
    /**
     * 将配置写入dirprm文件夹下
     * @param fileName
     * @param content
     */
    public int addParam(String fileName, String content) {
        int mkdirFile = executeBash("touch " + fileName);
        int writeToFile = executeBash("echo \"" + content + "\" >" + fileName);
        if (mkdirFile == 0 && writeToFile == 0) {
            return 0;
        } else {
            return mkdirFile | writeToFile;
        }
        /*try {
            SCPClient scpClient = connection.createSCPClient();
            SCPOutputStream scpOutputStream = scpClient.put(fileName, content.length(), remoteTargetDirectory, "0600");
            scpOutputStream.write(content.getBytes());
            scpOutputStream.flush();
            scpOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * 添加管理进程
     * @param fileName 管理进程名称
     */
    public void addManagerParam(String fileName) {
        String content = "port 7809\n" +
                "dynamicportlist 7810-7909\n" +
                "autorestart extract *, retries 5, waitminutes 3\n" +
                "purgeoldextracts ./dirdat/*,usecheckpoints,minkeepdays 3";
        addParam("dirprm/" + fileName + ".prm", content);
    }
    /**
     * 添加抽取进程，名称不带后缀
     * @param fileName 抽取进程extract文件名，不带后缀
     * @param userId 抽取用户id
     * @param password 抽取用户密码
     * @param mappingFileName 映射文件名称，不带后缀
     * @param schemaName 要抽取的schema
     */
    public void addExtractParam(String fileName, String userId, String password, String mappingFileName, String schemaName) {
        String content = "EXTRACT " + fileName + "\n" +
                "SETENV (NLS_LANG=AMERICAN_AMERICA.AL32UTF8)\n" +
                "SETENV (ORACLE_SID =\"SJCK\")\n" +
                "USERID " + userId + ",PASSWORD " + password + "\n" +
                "DYNAMICRESOLUTION\n" +
                "GETUPDATEBEFORES\n" +
                "DBOPTIONS ALLOWUNUSEDCOLUMN\n" +
                "REPORTCOUNT EVERY 30 MINUTES,RATE\n" +
                "WARNLONGTRANS 2h,CHECKINTERVAL 3m\n" +
                "EXTTRAIL ./dirdat/" + mappingFileName + ".def\n" +
                "table " + schemaName + ".*;\n";
        addParam("dirprm/" + fileName + ".prm", content);
    }

    /**
     * 添加pump进程，不带后缀
     * @param fileName pump进程文件名，不带后缀
     * @param rmtHost 目标端ip
     * @param mgrPort 目标端mgr端口
     * @param mappingFileName 映射文件名称，不带后缀
     * @param schemaName 要抽取的schema
     */
    public void addPumpParam(String fileName, String rmtHost,String mgrPort, String mappingFileName, String schemaName) {
        String content = "extract " + fileName + "\n" +
                "SETENV (NLS_LANG=\"AMERICAN_AMERICA.ALL32UTF8\")\n" +
                "PASSTHRU\n" +
                "DYNAMICRESOLUTION\n" +
                "RMTHOST " + rmtHost + ",MGRPORT " + mgrPort + "\n" +
                "RMTTRAIL ./dirdat/" + mappingFileName + ".def\n" +
                "TABLE " + schemaName + ".*;\n";
        addParam("dirprm/" + fileName + ".prm", content);
    }

    /**
     * 配置define文件(异构数据库间需要)
     * @param fileName 文件名，不带后缀
     * @param userId 抽取用户名
     * @param password 抽取用户密码
     * @param schemaName 要抽取的schema
     */
    public void addDefineParam(String fileName, String userId, String password, String schemaName) {
        String content = "DEFSFILE dirdef/" + fileName + ".def,PURGE\n" +
                "USERID " + userId + ", PASSWORD " + password + "\n" +
                "TABLE " + schemaName + ".*;";
        addParam("dirdef/" + fileName + ".def", content);
    }

    /**
     * 添加配置replicat进程
     * @param fileName replicat进程文件名，不带后缀
     * @param kafkaConfigFileName kafka配置文件名，不带后缀
     * @param mappingFileName 映射文件名，不带后缀
     * @param schemaName 要抽取的schema
     */
    public void addReplicateParam(String fileName, String kafkaConfigFileName, String mappingFileName, String schemaName) {
        String conetnt = "replicat " + fileName + "\n" +
                "setenv (NLS_LANG=\"AMERICAN_AMERICA.AL32UTF8\")\n" +
                "targetdb libfile libggjava.so set property=dirprm/" + kafkaConfigFileName + ".props\n" +
                "sourcedefs dirdef/" + mappingFileName + ".def\n" +
                "reportcount every 1 minutes, rate\n" +
                "grouptransops 10000\n" +
                "map " + schemaName + ".*,target " + schemaName + ".*;";
        addParam("dirprm/" + fileName + ".prm", conetnt);
    }

    /**
     * 添加生产者配置
     * @param fileName 生产者配置文件名称
     * @param kafkaUrl kafka的ip地址：端口即 ip:port
     */
    public void addProducerProperties(String fileName, String kafkaUrl) {
        String content = "bootstrap.servers=" + kafkaUrl + "\n" +
                "acks=1\n" +
                "reconnect.backoff.ms=1000\n" +
                "value.serializer=org.apache.kafka.common.serialization.ByteArraySerializer\n" +
                "key.serializer=org.apache.kafka.common.serialization.ByteArraySerializer\n" +
                "# 100KB per partition\n" +
                "batch.size=16384\n" +
                "linger.ms=0\n";
        addParam("dirprm/" + fileName + ".properties", content);
    }

    /**
     * 添加kafka配置
     * @param fileName kafka配置文件名
     * @param producerName 生产者文件名
     * @param schemaTopicName kafka产生Oracle数据库表的avro格式的schema的主题
     * @param kafkaAbsPath kafka在服务器的安装路径
     */
    public void addKafkaProps(String fileName, String producerName, String schemaTopicName, String kafkaAbsPath) {
        String content = "gg.handlerlist = kafkahandler\n" +
                "gg.handler.kafkahandler.type=kafka\n" +
                "gg.handler.kafkahandler.KafkaProducerConfigFile=" + producerName + ".properties\n" +
                "#The following resolves the topic name using the short table name\n" +
                "gg.handler.kafkahandler.topicMappingTemplate=${tableName}\n" +
                "#The following selects the message key using the concatenated primary keys\n" +
                "gg.handler.kafkahandler.keyMappingTemplate=${primaryKeys}\n" +
                "gg.handler.kafkahandler.format=avro_op\n" +
                "gg.handler.kafkahandler.SchemaTopicName=" + schemaTopicName + "\n" +
                "gg.handler.kafkahandler.BlockingSend =false\n" +
                "gg.handler.kafkahandler.includeTokens=false\n" +
                "gg.handler.kafkahandler.mode=op\n" +
                "goldengate.userexit.writers=javawriter\n" +
                "javawriter.stats.display=TRUE\n" +
                "javawriter.stats.full=TRUE\n" +
                "gg.log=log4j\n" +
                "gg.log.level=INFO\n" +
                "gg.report.time=30sec\n" +
                "#Sample gg.classpath for Apache Kafka\n" +
                "gg.classpath=dirprm/:" + kafkaAbsPath + "/libs/*\n" +
                "#Sample gg.classpath for HDP\n" +
                "#gg.classpath=/etc/kafka/conf:/usr/hdp/current/kafka-broker/libs/*\n" +
                "javawriter.bootoptions=-Xmx512m -Xms32m -Djava.class.path=ggjava/ggjava.jar ";
        addParam("dirprm/" + fileName + ".props", content);
    }

    /**
     * 将文件从源端上传到目标端
     * @param srcFileName
     * @param destIp
     * @param destUserName
     * @param destPassword
     * @throws Exception
     */
    public void moveFileFromSourceToDestination(String srcFileName, String destIp, String destUserName, String destPassword) throws Exception {
        Connection destConnection = getConnection(destIp, destUserName, destPassword);
        SCPClient srcScp = connection.createSCPClient();
        SCPClient destScp = destConnection.createSCPClient();
        SCPInputStream inputStream = srcScp.get(OGG_HOME + "dirdef/" + srcFileName);
        StringBuilder content = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            content.append(str);
        }
        SCPOutputStream outputStream = destScp.put(srcFileName, content.length(), BIG_OGG_HOME + "dirdef/", "0600");
        outputStream.write(content.toString().getBytes());
        outputStream.flush();
        outputStream.close();
    }
    public static void main(String[] args) throws Exception {
        // 源端
        String ip = "192.168.163.129", username = "oracle", password = "idea520";
        RemoteShellUtil shell = new RemoteShellUtil(ip, username, password);
        shell.addManagerParam("t_mgr");
        shell.addDefineParam("t_def", "ogg", "ogg", "sjck_bz");
        shell.addExtractParam("t_extract", "ogg", "ogg", "t_def", "sjck_bz");
        shell.addPumpParam("t_pump", "192.168.163.130", "7809", "t_def", "sjck_bz");
        // 从源端上传文件到目标端
        shell.moveFileFromSourceToDestination("t_def.def", "192.168.163.130", "oracle", "idea520");
        // 目标端
        ip = "192.168.163.130";
        shell = new RemoteShellUtil(ip, username, password);
        shell.addManagerParam("t_mgr");
        shell.addProducerProperties("t_producer", "192.168.163.130:9092");
        shell.addKafkaProps("t_kafka", "t_producer", "t_schema", "/opt/kafka");
        shell.addReplicateParam("t_replicat", "t_kafka", "t_def", "sjck_bz");
    }
}
