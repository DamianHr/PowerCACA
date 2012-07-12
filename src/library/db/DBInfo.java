package library.db;

/**
 * Created with IntelliJ IDEA.
 * User: regisbelson
 * Date: 11/07/12
 * Time: 12:32
 * To change this template use File | Settings | File Templates.
 */
public class DBInfo {
    private String username;
    private String password;
    private Integer port;
    private String hostname;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Integer getPort() {
        return port;
    }

    public String getHostname() {
        return hostname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
}
