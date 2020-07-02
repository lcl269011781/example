package com.lcl.dataxexample.util;

/**
 * @ClassName: DataxSupportDbType
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/2 9:17
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
public class DataxSupportDbType {

    public enum DbType{

        MYSQL(1,"MYSQL"),
        ORACLE(2,"ORACLE");

        private int code;
        private String desc;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        DbType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

}
