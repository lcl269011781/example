package com.amc.pojo;

import com.ect888.bean.JsonCommon;

import java.util.ArrayList;

/**
 * 
 * 209查询结果
 * 对应json
{
    "error_no":"0",
    "results":[
        {
            "cerkey":"",
            "respinfo":"认证一致(通过)",
            "status":"00",
            "mpssim":"0.0",
            "biztyp":"A001",
            "sysSeqNb":"19103520190308802006580001",
            "respcd":"5000",
            "certseq":"341227198912173710",
            "accountName":"",
            "name":"姓名",
            "ptyAcct":"ect888_public_demo",
            "ptycd":"ect888_public",
            "localsim":"",
            "telephone":""
        }
    ],
    "dsName":[
        "results"
    ],
    "error_info":""
}
 * 
 * @author Chunliang.Li
 *
 */
public class Json209 extends JsonCommon{

	private ArrayList<Result209> results;

	public ArrayList<Result209> getResults() {
		return results;
	}

	public void setResults(ArrayList<Result209> results) {
		this.results = results;
	}
	
	

}
