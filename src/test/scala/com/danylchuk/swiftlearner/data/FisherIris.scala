package com.danylchuk.swiftlearner.data

import scala.io.Source

/**
  * The classic Fisher Iris flower dataset as an example.
  *
  * Ref: https://en.wikipedia.org/wiki/Iris_flower_data_set
  *      http://www.math.uah.edu/stat/data/Fisher.html
  */
object FisherIris {
  lazy val irisData = {
    def toDataRow(csv: String): (Int, Vector[Double]) = {
      val args = csv.split(',')
      val itemClass = args.head.toInt
      val parameters = args.tail.map(_.toDouble).toVector
      (itemClass, parameters)
    }
    val dataFileURI = this.getClass.getClassLoader.getResource("FisherIris.csv").toURI
    val csvData = Source.fromFile(dataFileURI).getLines().toSeq.tail  // skip the header
    csvData.map(toDataRow)
  }

  /** Split to 2/3 training and 1/3 test data at random */
  def trainingAndTestData(randomSeed: Option[Long] = None) = {
    val r = new scala.util.Random()
    randomSeed.foreach(r.setSeed)
    r.shuffle(irisData).splitAt(irisData.size * 2 / 3)
  }

  /** Split to 2/3 training and 1/3 test data at random (Double version) */
  def trainingAndTestDataDouble(randomSeed: Option[Long] = None) = {
    val r = new scala.util.Random()
    randomSeed.foreach(r.setSeed)
    val data = irisData map Function.tupled((a, xs) => (a, xs.map(_.toDouble)))
    r.shuffle(data).splitAt(data.size * 2 / 3)
  }

  /** Split to 2/3 training and 1/3 test data at random (Float version) */
  def trainingAndTestDataFloat(randomSeed: Option[Long] = None) = {
    val r = new scala.util.Random()
    randomSeed.foreach(r.setSeed)
    val floatData = irisData map Function.tupled((a, xs) => (a, xs.map(_.toFloat)))
    r.shuffle(floatData).splitAt(floatData.size * 2 / 3)
  }
}
