package com.github.maprohu.jsext

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

/**
 * Created by marci on 31-10-2015.
 */


object CreateMap {

  def createMap: Map[String, Any] = macro CreateMapImpl.createMap

}

object CreateMapImpl {

  def argsToMap(c:Context)(args: Seq[c.universe.ValDef]) = {
    import c.universe._
    val pairs = args.map(vd => q"${vd.name.toString} -> ${vd.name}" )
    q"_root_.scala.collection.immutable.Map(..$pairs)"
  }
  def createMap(c: Context) = createMapTrf(c)(t => t)
  def createMapTrf(c: Context)(trf : c.Tree => c.Tree) = {
    import c.universe._
    def go(trees: List[c.Tree]) :  c.Tree = trees match {
      case q"def $dname(..$dvars) = $dret" :: _ => trf(argsToMap(c)(dvars))
      case _ :: ts => go(ts)
    }
    go(enclosingTrees(c).toList)
  }

  def enclosingTrees(c: Context): Seq[c.Tree] =
    c.asInstanceOf[scala.reflect.macros.contexts.Context].callsiteTyper.context.enclosingContextChain.map(_.tree.asInstanceOf[c.Tree])

}
