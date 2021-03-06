package pl.tertionondatur.sparkdatalog.magicsets.example

import org.apache.spark.SparkContext
import pl.appsilon.marek.sparkdatalog.{Relation, Database}

object Samples {

  def database(sc: SparkContext): Database = {
    val ups = sc.parallelize(Seq((1, 5), (1, 6), (6,13), (7,14), (8,14), (9,15), (10,15)))
    val flats = sc.parallelize(Seq((7, 6), (13,14),(13,15),(16,13)))
    val downs = sc.parallelize(Seq((12,6),(13,6),(7,2),(8,3),(9,4)))

    Database(Relation.binary("Up", ups), Relation.binary("Flat", flats), Relation.binary("Down", downs))
  }

  def program: String =
      """
        |Rsg(x,y) :- Flat(x,y).
        |Rsg(x,y):- Up(x,x1),Rsg(y1,x1),Down(y1,y).
        |Q(x) :- Rsg(13,x).
      """.stripMargin

}
