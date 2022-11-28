package org.example.entity;

import lombok.Data;

/**
 * QarDecodeMsg 译码完成消息通知
 */
@Data
public class QarDecodeMsg {

	private String planeNumber; // 飞机注册号: B1234

	private long startTime; // qar译码结果中飞行记录的开始时间

	private long endTime; // qar译码结果中飞行记录的结束时间

	private long qarId; // qar ID

	private String qarFileName; // qar文件名

	private long fileSize; // qar 文件大小，字节

	private long count; // 本次译码成果的记录条数

	private long categoryId; // 本次译码使用的构型
}
