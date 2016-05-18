package io.buoyant.namer.zk

import com.twitter.finagle.{Name, NameTree, Namer, Path}
import com.twitter.util.Activity

/**
 * This namer accepts paths of the form /<zkHosts>/<zkPath>.  The zkPath is the location
 * in ZooKeeper of a leader group.  This namer resolves to the addresses stored in the data of
 * the leader of the group.
 */
class leader extends Namer {
  override def lookup(path: Path): Activity[NameTree[Name]] = {
    val Path.Utf8(hosts) = path.take(1)
    val namer = new ZkLeaderNamer(Path.empty, hosts)
    namer.lookup(path.drop(1))
  }
}