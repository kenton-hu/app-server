package cn.wildfirechat.app.wfchat.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_group_member")
public class TGroupMember {
    @Id
    @Column(length = 11)
    private int id;

    @Column(name = "_gid")
    private String gid;

    @Column(name = "_mid")
    private String mid;

    @Column(name = "_alias")
    private String alias;

    @Column(name = "_type")
    private int type;

    @Column(name = "_dt")
    private long dt;

    @Column(name = "_create_dt")
    private long createDt;

    @Column(name = "_extra")
    private String extra;
}
