package org.example;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.entity.QarDecodeMsg;

import java.util.Date;
import java.util.Properties;

/**
 * QarProducer
 *
 */
public class QarProducer
{
    public static void main(String[] args)
    {
        System.out.println( "*** QarProducer Test ***" );

        String QAR_GROUP = "qar-group";
        String QAR_TOPIC_SUCCESS = "qar-topic-success"; // QAR译码成功通知

        // Assign topicName to string variable
        String topicName = QAR_TOPIC_SUCCESS;

        // create instance for properties to access producer configs
        Properties props = new Properties();

        // Assign localhost id 192.168.1.105
        props.put("bootstrap.servers", "192.168.43.227:9092");

        props.put("acks","all");

        // If the request fails, the producer can automatically retry,
        props.put("retries", 10);

        // Specify buffer size in config
        props.put("batch.size", 16384);

        props.put("buffer.memory", 33554432);

        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // changeStatus StopMessage(planeNo=B1870, organization=HO, firstTime=1660693394000, lastTime=1660701438000,
        // fileName=/mnt/hgfs/centos_7_share2/B-1870_20220817015623.wgl/raw.dat, fileSize=16490496,
        // processStart=1669622880824, processEnd=1669622913080, totalCount=8041, status=SUCC,
        // qarId=1660701383000, categoryId=188, errorInfo=null) / true / 1001 / http://127.0.0.1:8080/data/updateQarDecode / true
//        QarDecodeMsg msg = new QarDecodeMsg();
//        msg.setCount(8041);
//        msg.setFileSize(16490496);
//        msg.setPlaneNumber("B1870");
//        msg.setStartTime(1660693394000L);
//        msg.setEndTime(1660701438000L);
//        msg.setQarId(1660701383000L);
//        msg.setQarFileName("/home/isoft/qar_file/B-1870_20220817015623.wgl/raw.dat");
//        msg.setCategoryId(188);

        // {"categoryId":188,"count":7944,"endTime":1670223653000,"fileSize":16269312,"planeNumber":"B8236",
        // "qarFileName":"D:\\big_data\\ho_data\\20221120-20221205\\B8236\\B-8236_20221205070334.wgl\\raw.dat","qarId":1670223814000,"startTime":1670215708000}
        QarDecodeMsg msg = new QarDecodeMsg();
        msg.setCount(8465);
        msg.setFileSize(17358848);
        msg.setPlaneNumber("B324C");
        msg.setStartTime(1669431103000L);
        msg.setEndTime(1669439572000L);
        msg.setQarId(1669431103000L);
        msg.setQarFileName("D:\\big_data\\ho_data\\20221120-20221205\\B324C\\B-324C_20221126051826.wgl\\raw.dat");
        msg.setCategoryId(185);

        // {"categoryId":185,"count":8465,"endTime":1669439572000,"fileSize":17358848,"planeNumber":"B324C",
        // "qarFileName":"D:\\big_data\\ho_data\\20221120-20221205\\B324C\\B-324C_20221126051826.wgl\\raw.dat","qarId":1669439906000,"startTime":1669431103000}
        String json = JSONObject.toJSONString(msg);

        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        //for (int i = 0; i < 10; i++) {
        producer.send(new ProducerRecord<String, String>(topicName,
            "qar-" + new Date(), // key
                json // value
        ));
        //}
        System.out.println("message send successfully");
        producer.close();
    }
}
