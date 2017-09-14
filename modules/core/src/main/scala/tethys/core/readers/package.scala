package tethys.core

import java.io.{Reader, StringReader}

import tethys.core.readers.tokens.{TokenIterator, TokenIteratorProducer}

package object readers {

  implicit class StringReaderOps(val json: String) extends AnyVal {
    def jsonAs[A](implicit jsonReader: JsonReader[A], producer: TokenIteratorProducer): Either[ReaderError, A] = {
      new StringReader(json).readJson[A]
    }
  }

  implicit class ReaderReaderOps(val reader: Reader) extends AnyVal {
    def readJson[A](implicit jsonReader: JsonReader[A], producer: TokenIteratorProducer): Either[ReaderError, A] = {
      producer.fromReader(reader).readJson[A]
    }
  }

  implicit class TokenIteratorOps(val tokenIterator: TokenIterator) extends AnyVal {
    def readJson[A](implicit jsonReader: JsonReader[A]): Either[ReaderError, A] = {
      jsonReader.read(tokenIterator)(FieldName())
    }
  }
}
