package tethys.derivation.builder

import org.scalatest.{FlatSpec, Matchers}
import tethys.JsonReader
import tethys.derivation.builder.ReaderDescriptionTest.Foo
import tethys.derivation.semiauto._

class ReaderDescriptionTest extends FlatSpec with Matchers {

  behavior of "describe"

  it should "build empty description from empty builder" in {
    describe(ReaderBuilder[Foo]) shouldBe ReaderDescription[Foo](Seq())
  }

  it should "build description for ExtractFieldAs operation" in {
    val fun: Option[Int] => Int = _.getOrElse(1)

    val description = describe {
      ReaderBuilder[Foo]
        .extract(_.a).as[Option[Int]](fun)
    }

    description shouldBe ReaderDescription[Foo](Seq(
      ReaderDescription.BuilderOperation.ExtractFieldAs("a", fun)
    ))
  }

  it should "build description for ExtractFieldValue operation from lambdas" in {
    val fun: (String, Any) => Int = (s, a) => 1

    val description = describe {
      ReaderBuilder[Foo]
        .extract(_.a).from(_.b, _.c)(fun)
    }

    description shouldBe ReaderDescription[Foo](Seq(
      ReaderDescription.BuilderOperation.ExtractFieldValue(
        field = "a",
        from = Seq(
          ReaderDescription.Field[String]("b"),
          ReaderDescription.Field[String]("c")
        ),
        fun = fun
      )
    ))
  }

  it should "build description for ExtractFieldValue operation from ReaderField's" in {
    val fun: (String, Any) => Int = (s, a) => 1

    val description = describe {
      ReaderBuilder[Foo]
        .extract(_.a).from('b.as[String], "c".as[Any])(fun)
    }

    description shouldBe ReaderDescription[Foo](Seq(
      ReaderDescription.BuilderOperation.ExtractFieldValue(
        field = "a",
        from = Seq(
          ReaderDescription.Field[String]("b"),
          ReaderDescription.Field[String]("c")
        ),
        fun = fun
      )
    ))
  }

  it should "build description for ExtractFieldValue operation from lambdas and lambdas" in {
    val fun: (String, Any) => Int = (s, a) => 1

    val description = describe {
      ReaderBuilder[Foo]
        .extract(_.a).from(_.b).and(_.c)(fun)
    }

    description shouldBe ReaderDescription[Foo](Seq(
      ReaderDescription.BuilderOperation.ExtractFieldValue(
        field = "a",
        from = Seq(
          ReaderDescription.Field[String]("b"),
          ReaderDescription.Field[String]("c")
        ),
        fun = fun
      )
    ))
  }

  it should "build description for ExtractFieldValue operation from lambdas and ReaderField's" in {
    val fun: (String, Any) => Int = (s, a) => 1

    val description = describe {
      ReaderBuilder[Foo]
        .extract(_.a).from(_.b).and('c.as[Any])(fun)
    }

    description shouldBe ReaderDescription[Foo](Seq(
      ReaderDescription.BuilderOperation.ExtractFieldValue(
        field = "a",
        from = Seq(
          ReaderDescription.Field[String]("b"),
          ReaderDescription.Field[String]("c")
        ),
        fun = fun
      )
    ))
  }

  it should "build description for ExtractFieldValue operation from ReaderField's and lambdas" in {
    val fun: (String, Any) => Int = (s, a) => 1

    val description = describe {
      ReaderBuilder[Foo]
        .extract(_.a).from('b.as[String]).and(_.c)(fun)
    }

    description shouldBe ReaderDescription[Foo](Seq(
      ReaderDescription.BuilderOperation.ExtractFieldValue(
        field = "a",
        from = Seq(
          ReaderDescription.Field[String]("b"),
          ReaderDescription.Field[String]("c")
        ),
        fun = fun
      )
    ))
  }

  it should "build description for ExtractFieldValue operation from ReaderField's and ReaderField's" in {
    val fun: (String, Any) => Int = (s, a) => 1

    val description = describe {
      ReaderBuilder[Foo]
        .extract(_.a).from('b.as[String]).and('c.as[Any])(fun)
    }

    description shouldBe ReaderDescription[Foo](Seq(
      ReaderDescription.BuilderOperation.ExtractFieldValue(
        field = "a",
        from = Seq(
          ReaderDescription.Field[String]("b"),
          ReaderDescription.Field[String]("c")
        ),
        fun = fun
      )
    ))
  }

  it should "build description for ExtractFieldReader operation from lambdas" in {
    val fun: (String, Any) => JsonReader[Int] = (s, a) => JsonReader.intReader

    val description = describe {
      ReaderBuilder[Foo]
        .extractReader(_.a).from(_.b, _.c)(fun)
    }

    description shouldBe ReaderDescription[Foo](Seq(
      ReaderDescription.BuilderOperation.ExtractFieldReader(
        field = "a",
        from = Seq(
          ReaderDescription.Field[String]("b"),
          ReaderDescription.Field[String]("c")
        ),
        fun = fun
      )
    ))
  }

  it should "build description for ExtractFieldReader operation from ReaderField's" in {
    val fun: (String, Any) => JsonReader[Int] = (s, a) => JsonReader.intReader

    val description = describe {
      ReaderBuilder[Foo]
        .extractReader(_.a).from('b.as[String], "c".as[Any])(fun)
    }

    description shouldBe ReaderDescription[Foo](Seq(
      ReaderDescription.BuilderOperation.ExtractFieldReader(
        field = "a",
        from = Seq(
          ReaderDescription.Field[String]("b"),
          ReaderDescription.Field[String]("c")
        ),
        fun = fun
      )
    ))
  }

  it should "build description for ExtractFieldReader operation from lambdas and lambdas" in {
    val fun: (String, Any) => JsonReader[Int] = (s, a) => JsonReader.intReader

    val description = describe {
      ReaderBuilder[Foo]
        .extractReader(_.a).from(_.b).and(_.c)(fun)
    }

    description shouldBe ReaderDescription[Foo](Seq(
      ReaderDescription.BuilderOperation.ExtractFieldReader(
        field = "a",
        from = Seq(
          ReaderDescription.Field[String]("b"),
          ReaderDescription.Field[String]("c")
        ),
        fun = fun
      )
    ))
  }

  it should "build description for ExtractFieldReader operation from lambdas and ReaderField's" in {
    val fun: (String, Any) => JsonReader[Int] = (s, a) => JsonReader.intReader

    val description = describe {
      ReaderBuilder[Foo]
        .extractReader(_.a).from(_.b).and('c.as[Any])(fun)
    }

    description shouldBe ReaderDescription[Foo](Seq(
      ReaderDescription.BuilderOperation.ExtractFieldReader(
        field = "a",
        from = Seq(
          ReaderDescription.Field[String]("b"),
          ReaderDescription.Field[String]("c")
        ),
        fun = fun
      )
    ))
  }

  it should "build description for ExtractFieldReader operation from ReaderField's and lambdas" in {
    val fun: (String, Any) => JsonReader[Int] = (s, a) => JsonReader.intReader

    val description = describe {
      ReaderBuilder[Foo]
        .extractReader(_.a).from('b.as[String]).and(_.c)(fun)
    }

    description shouldBe ReaderDescription[Foo](Seq(
      ReaderDescription.BuilderOperation.ExtractFieldReader(
        field = "a",
        from = Seq(
          ReaderDescription.Field[String]("b"),
          ReaderDescription.Field[String]("c")
        ),
        fun = fun
      )
    ))
  }

  it should "build description for ExtractFieldReader operation from ReaderField's and ReaderField's" in {
    val fun: (String, Any) => JsonReader[Int] = (s, a) => JsonReader.intReader

    val description = describe {
      ReaderBuilder[Foo]
        .extractReader(_.a).from('b.as[String]).and('c.as[Any])(fun)
    }

    description shouldBe ReaderDescription[Foo](Seq(
      ReaderDescription.BuilderOperation.ExtractFieldReader(
        field = "a",
        from = Seq(
          ReaderDescription.Field[String]("b"),
          ReaderDescription.Field[String]("c")
        ),
        fun = fun
      )
    ))
  }


}

object ReaderDescriptionTest {
  case class Foo(a: Int, b: String, c: Any)
}
