package aggregator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "serverId", "account", "amount", "timestamp"})
public class Account {

    @JsonProperty("id")
    private String id;

    @JsonProperty("serverId")
    private String serverId;

    @JsonProperty("account")
    private String account;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("timestamp")
    private String timestamp;

    public Account() {}

    public Account(String id, String serverId, String account, String amount, String timestamp) {
        this.id = id;
        this.serverId = serverId;
        this.account = account;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
