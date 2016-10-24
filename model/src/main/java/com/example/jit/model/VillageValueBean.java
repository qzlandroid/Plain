package com.example.jit.model;

import java.util.List;

/**
 * 乡镇村庄信息的数据类
 * Created by Max on 2016/7/6.
 */
public class VillageValueBean {


    /**
     * id : 7
     * name : A
     * super : 0
     * village : [{"id":27,"name":"A111","super":7},{"id":25,"name":"1212","super":7},{"id":24,"name":"12","super":7},{"id":22,"name":"a13","super":7},{"id":19,"name":"a12","super":7},{"id":18,"name":"aaaaaaaaaaaaaaaaaaa11","super":7},{"id":16,"name":"a10","super":7},{"id":15,"name":"a9","super":7},{"id":14,"name":"a8","super":7},{"id":13,"name":"a6","super":7},{"id":12,"name":"a5","super":7},{"id":11,"name":"a4","super":7},{"id":10,"name":"a3","super":7},{"id":9,"name":"a2","super":7},{"id":8,"name":"a1","super":7}]
     */

    private int id;
    private String name;
    /**
     * id : 27
     * name : A111
     * super : 7
     */

    private List<VillageBean> village;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<VillageBean> getVillage() {
        return village;
    }

    public void setVillage(List<VillageBean> village) {
        this.village = village;
    }

    public static class VillageBean {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
