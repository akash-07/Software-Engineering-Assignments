package Template_Pattern

/**
  * Created by @kash on 9/14/2017.
  */
object template_pattern_scala extends App{

  def makeGradereport(numToLetter: List[Double] => List[String], printGradeReport: List[String] => Unit) =
    (grades: List[Double]) => printGradeReport(numToLetter(grades))

  def numToLetter(grades: List[Double]): List[String]  = {
    def convert(d: Double): String =
      if (d == 10) "S"
      else if (d < 10 && d >= 9) "A"
      else if (d < 9 && d >= 8) "B"
      else if (d < 8 && d >= 7) "C"
      else if (d < 7 && d >= 6) "D"
      else if (d >= 0) "E"
      else "N/A"

    grades.map(convert)
  }

  def printHistogram(letterGrades: List[String]): Unit = {
    val gradeCount = letterGrades.groupBy(identity).map(tup => (tup._1,tup._2.length)).toSeq.sorted;
    println("----Printing grade histogram----")
    for(gradeTuple <- gradeCount) {
      println(gradeTuple._1 + ": " + ("*" * gradeTuple._2))
    }
  }

  val fullGradeConverter = makeGradereport(numToLetter,printHistogram)
  val grades = List(9.0, 8.4, 7.3, 8.5, 10.0, 9.2, 8.9)
  fullGradeConverter(grades)
}