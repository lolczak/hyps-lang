package hyps.lang.compiler

import java.net.URL
import scala.io.Source

package object prelude {

  type =|>[-A, +B] = PartialFunction[A, B]

  type \/[+A, +B] = Either[A, B]

  implicit class ResourceHelper(val sc: StringContext) extends AnyVal {

    def findUrl(path: String): Option[URL] = Option(Thread.currentThread().getContextClassLoader.getResource(path))

    def load(path: String): Option[String] = findUrl(path) map (Source.fromURL(_).mkString)

    def resource(args: Any*): String = {
      val strings     = sc.parts.iterator
      val expressions = args.iterator
      val buf         = new StringBuffer(strings.next())
      while (strings.hasNext) {
        buf append expressions.next()
        buf append strings.next()
      }
      val path = buf.toString
      load(path).getOrElse(throw new RuntimeException(s"Cannot load $path"))
    }

  }

}
