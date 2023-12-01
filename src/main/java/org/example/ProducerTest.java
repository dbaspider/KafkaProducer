package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Date;
import java.util.Properties;

/**
 * ProducerTest
 *
 */
public class ProducerTest
{
    private static volatile int g_sn = 0;

    public static void main( String[] args )
    {
        System.out.println( "*** Producer Test ***" );

        if (args.length == 0) {
            System.out.println("Enter topic name");
            //return;
        }

        // Assign topicName to string variable
        String topicName = "helloTest"; //args[0];

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

        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        for (int i = 0; i < 10; i++) {
            g_sn++;
            producer.send(new ProducerRecord<String, String>(topicName,
                    Integer.toString(g_sn), // key
                    Integer.toString(i) + "[" + new Date()  + "]" // value
            ));
        }
        System.out.println("message send successfully");
        producer.close();
    }
}
