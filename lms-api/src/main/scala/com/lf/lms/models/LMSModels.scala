package com.lf.lms.models

import play.api.libs.json.{Format, Json}

object LMSModels {

  case class SQSMessage(MessageId: String, ReceiptHandle: String, MD5OfBody: String, Body: BorrowerProfileBody, Attributes: String, MessageAttributes: String)

  case class BorrowerProfileBody(borrowerId: String, cfaId: String, loanLimit: Double)

  case class UserProfile(firstName: String, middleName: String, lastName: String, mobileNumber: String, emailAddress: String, password: String, confirmPassword: String)

  object UserProfile {
    /**
      * Format for converting user messages to and from JSON.
      *
      * This will be picked up by a Lagom implicit conversion from Play's JSON format to Lagom's message serializer.
      */
    implicit val format: Format[UserProfile] = Json.format[UserProfile]
  }

}
