package com.loanframe.lms.impl

import com.amazonaws.auth.{AWSStaticCredentialsProvider, BasicAWSCredentials}
import com.amazonaws.services.sqs.model.{ReceiveMessageRequest, ReceiveMessageResult}
import com.amazonaws.services.sqs.{AmazonSQS, AmazonSQSClientBuilder}
import com.loanframe.lms.util.Constants
import javax.inject.Inject
import play.api.Configuration

class SQSSubscriber @Inject()(configuration: Configuration) {

  val aws_region: String = configuration.getString(Constants.aws_region).getOrElse("")
  val aws_sqs_access_key_id: String = configuration.getString(Constants.aws_sqs_access_key_id).getOrElse("")
  val aws_sqs_secret_access_key: String = configuration.getString(Constants.aws_sqs_secret_access_key).getOrElse("")
  val aws_sqs_queue_url: String = configuration.getString(Constants.aws_sqs_queue_url).getOrElse("")

  val credentials = new BasicAWSCredentials(aws_sqs_access_key_id, aws_sqs_secret_access_key)
  val amazonSqsClient: AmazonSQS = AmazonSQSClientBuilder.standard().withRegion(aws_region).withCredentials(new AWSStaticCredentialsProvider(credentials)).build()

  def fetchMsgFromSQS(): ReceiveMessageResult = {
    val messageRequest = new ReceiveMessageRequest
    messageRequest.setQueueUrl(aws_sqs_queue_url)
    messageRequest.setMaxNumberOfMessages(10)
    amazonSqsClient.receiveMessage(messageRequest)
  }

  def deleteMsgFromSQS(receiptHandle: String) = {
    amazonSqsClient.deleteMessage(aws_sqs_queue_url, receiptHandle)
  }
}
