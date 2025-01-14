package org.sirix.node.interfaces.immutable;

import net.openhft.chronicle.bytes.Bytes;
import net.openhft.chronicle.bytes.BytesOut;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.sirix.node.NodeKind;
import org.sirix.node.interfaces.DataRecord;
import org.sirix.node.interfaces.Node;

import java.nio.ByteBuffer;

/**
 * An immutable node.
 *
 * @author Johannes Lichtenberger
 */
public interface ImmutableNode extends DataRecord {

  @Override
  NodeKind getKind();

  /**
   * Determines if {@code other} is the same item.
   *
   * @param other the other node
   * @return {@code true}, if it is the same item, {@code false} otherwise
   */
  boolean isSameItem(@Nullable Node other);

  /**
   * Getting the stored hash.
   *
   * @return the hash code
   */
  long getHash();

  /**
   * Compute the hash code.
   *
   * @param bytes the bytes to serialize the node to before hashing
   * @return the computed hash code
   */
  long computeHash(Bytes<ByteBuffer> bytes);

  /**
   * Gets key of the context item's parent.
   *
   * @return parent key
   */
  long getParentKey();

  /**
   * Declares, whether the item has a parent.
   *
   * @return {@code true}, if item has a parent, {@code false} otherwise
   */
  boolean hasParent();
}
