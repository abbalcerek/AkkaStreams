package my.akkastreams

import twitter4j.{TwitterStreamFactory, TwitterFactory}
import twitter4j.conf.ConfigurationBuilder

/**
  * Created by Adam on 14/03/2016.
  */
object TwitterSourceFactory {

  def config = {
    val configurationBuilder = new ConfigurationBuilder()
      .setDebugEnabled(true)
      .setOAuthConsumerKey("naGeA3lY2bBrkMlLg0H2cyQl0")
      .setOAuthConsumerSecret("0aPhAeh0u4XM6rjWfZfoZYVFx37C2A4mWjWP4RnLvqxlLU0VRL")
      .setOAuthAccessToken("2576300359-OcmLx79v4O7KiGydPqAb29MzYnQDgS67nurKevc")
      .setOAuthAccessTokenSecret("vLXaFE8n7yB1H4ZvAVOnCvITIpIJQNNhObOdcEmCVUAB5")
    configurationBuilder.build()
  }

  def jTwitter() = new TwitterFactory(config).getInstance()

  def jTwitterStream() = new TwitterStreamFactory(config).getInstance()

}
