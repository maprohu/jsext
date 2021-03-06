package com.github.maprohu.jsext

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context
import scala.scalajs.js.JSConverters._

/**
 * Created by marci on 31-10-2015.
 */


object Options {

  def options[T]: T = macro OptionsImpl.createOptions[T]

}

object OptionsImpl {

  def createOptions[T : c.WeakTypeTag](c: Context) =
  {
    import c.universe._
    val tpe = weakTypeOf[T]
    CreateMapImpl.createMapTrf(c) { t =>
      val mod = symbolOf[OptionsImpl.type].asClass.module
      val mem = mod.info.member(TermName("toOptions"))
      q"$mem[$tpe]($t)"
    }
  }

  def toOptions[T](map: Map[String, Any]) =
    map.toJSDictionary.asInstanceOf[T]

}
