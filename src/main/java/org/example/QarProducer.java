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
    public static void main( String[] args )
    {
        System.out.println( "*** QarProducer Test ***" );

        String QAR_GROUP = "qar-group";
        String QAR_TOPIC_SUCCESS = "qar-topic-success"; // QAR译码成功通知

        // Assign topicName to string variable
        String topicName = QAR_TOPIC_SUCCESS;

        // create instance for properties to access producer configs
        Properties props = new Properties();

        // Assign localhost id 192.168.1.105
        props.put("bootstrap.servers","127.0.0.1:9092");

        props.put("acks","all");

        // If the request fails, the producer can automatically retry,
        props.put("retries", 0);

        // Specify buffer size in config
        props.put("batch.size", 16384);

        props.put("buffer.memory", 33554432);

        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        QarDecodeMsg msg = new QarDecodeMsg();
        msg.setCount(1000);
        msg.setFileSize(2000);
        msg.setPlaneNumber("B1645");
        msg.setStartTime(1660712526000L);
        msg.setEndTime(1660712535000L);
        msg.setQarId(1660724488000L);
        msg.setQarFileName("/home/isoft/qar_file/B-1645_20220817082128.wgl/raw.dat");

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
