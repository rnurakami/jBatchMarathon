package com.sample.jbatchmarathon.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Runnerを表すドメインオブジェクトです。
 *
 * @author akira_abe
 */
public class Runner implements Serializable {

    private static final Logger LOG = Logger.getLogger(Runner.class.getName());

    /**
     * ID
     */
    private Integer id;
    /**
     * 名前
     */
    private String name;
    /**
     * 誕生日
     */
    private Date birthDay;
    /**
     * 参加したレース
     */
    private List<Race> races = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public List<Race> getRaces() {
        return races;
    }

    public void setRaces(List<Race> races) {
        this.races = races;
    }

    @Override
    public String toString() {
        return "Runner{" + "name=" + name + ", birthDay=" + birthDay + ", races=" + races + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.birthDay);
        hash = 47 * hash + Objects.hashCode(this.races);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Runner other = (Runner) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.birthDay, other.birthDay)) {
            return false;
        }
        if (!Objects.equals(this.races, other.races)) {
            return false;
        }
        return true;
    }

    public Object createOutputData() {
        //FIXME:ひとまず動作確認用の実装をしました。
        OutputData data = new OutputData();
        data.setContents(this.toString());
        
        return data;
    }

    public void add(Race race) {
        this.races.add(race);
    }
}
