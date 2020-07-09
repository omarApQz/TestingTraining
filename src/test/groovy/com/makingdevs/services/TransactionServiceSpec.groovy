package com.makingdevs.services

import spock.lang.*

class TransactionServiceSpec extends Specification {

  def "Cuenta los registros para una tabla no esperada"(){
    given:
      TransactionService service = new TransactionService()
      String tableName = null
    when:
      service.countRecordsFor(tableName)
    then:
      thrown RuntimeException
  }

  def "Busca los registros para una tabla no esperada"(){
    given:
      TransactionService service = new TransactionService()
      String tableName = null
    when:
      service.findAll(tableName)
    then:
      RuntimeException ex = thrown()
      ex.message == "Cannot find !!!"
  }

  def "Altera una tabla que no existe"(){
    given:
      TransactionService service = new TransactionService()
      String tableName = null
    when:
      service.updateTable(tableName)
    then:
      notThrown(NullPointerException)
      //thrown NullPointerException
  }

  def "do a payment with a quantity"() {
    given:
      TransactionService service = new TransactionService()
    and:
      //def paymentGatewayMock = Mock(PaymentGateway)
      PaymentGateway paymentGatewayMock = Mock()
      service.paymentGateway = paymentGatewayMock
    and:
      def amount = 100
    when:
      int result = service.doPayment(amount)
    then:
      1 * paymentGatewayMock.authorize(_)
  }

  def "do many payments with many quantities"() {
    given:
      TransactionService service = new TransactionService()
    and:
      //def paymentGatewayMock = Mock(PaymentGateway)
      PaymentGateway paymentGatewayMock = Mock()
      service.paymentGateway = paymentGatewayMock
    and:
      def amounts = [100.0,200.0,300.0]
    when:
      def results = service.doManyPayments(amounts)
    then:
      2 * paymentGatewayMock.authorize(_)
  }
}
