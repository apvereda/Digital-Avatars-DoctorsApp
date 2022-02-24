package com.apvereda.db;

import android.util.Log;

import com.apvereda.uDataTypes.SBoolean;
import com.apvereda.utils.DigitalAvatar;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Expression;
import com.couchbase.lite.Meta;
import com.couchbase.lite.MutableDocument;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryBuilder;
import com.couchbase.lite.Result;
import com.couchbase.lite.ResultSet;
import com.couchbase.lite.SelectResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReputationOpinion {

    private String uid;
    private String reputee;
    private String scope;
    private SBoolean reputation;
    private Date expirationDate;

    public ReputationOpinion(String uid, String reputee, String scope, SBoolean reputation) {
        this.uid = uid;
        this.reputee = reputee;
        this.scope = scope;
        this.reputation = reputation;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getReputee() {
        return reputee;
    }

    public void setReputee(String reputee) {
        this.reputee = reputee;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public SBoolean getReputation() {
        return reputation;
    }

    public void setReputation(SBoolean reputation) {
        this.reputation = reputation;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public static void createReputation(ReputationOpinion t){
        MutableDocument opinionDoc = new MutableDocument();
        opinionDoc.setString("type", "Reputation");
        opinionDoc.setString("Reputee", t.getReputee());
        opinionDoc.setString("Scope", t.getScope());
        opinionDoc.setDouble("Belief", t.getReputation().belief());
        opinionDoc.setDouble("Disbelief", t.getReputation().disbelief());
        opinionDoc.setDouble("Uncertainty", t.getReputation().uncertainty());
        opinionDoc.setDouble("BaseRate", t.getReputation().baseRate());

        Log.i("Digital Avatars", "creando reputación... "+ opinionDoc.getString("Reputee"));
        DigitalAvatar.getDA().saveDoc(opinionDoc);
    }

    public static List<ReputationOpinion> getReputationforReputee(String uid){
        ArrayList<ReputationOpinion> resultList = new ArrayList<ReputationOpinion>();
        Query query = QueryBuilder
                .select(SelectResult.expression(Meta.id),
                        SelectResult.property("Reputee"),
                        SelectResult.property("Scope"),
                        SelectResult.property("Belief"),
                        SelectResult.property("Disbelief"),
                        SelectResult.property("Uncertainty"),
                        SelectResult.property("BaseRate"))
                .from(DigitalAvatar.getDataSource())
                .where(Expression.property("type").equalTo(Expression.string("Reputation"))
                        .and(Expression.property("Reputee").equalTo(Expression.string(uid))));

        try {
            ResultSet rs = query.execute();
            for (Result result : rs) {
                //Dictionary result = r.getDictionary(0);
                ReputationOpinion t = new ReputationOpinion(result.getString("id"), result.getString("Reputee"), result.getString("Scope"),
                        new SBoolean(result.getDouble("Belief"), result.getDouble("Disbelief"),
                                result.getDouble("Uncertainty"), result.getDouble("BaseRate")));
                resultList.add(t);
            }
        } catch (CouchbaseLiteException e) {
            Log.e("CouchbaseError", e.getLocalizedMessage());
        }
        return resultList;
    }
}
