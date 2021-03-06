package com.networknt.eventuate.jdbc;

import com.networknt.eventuate.common.Aggregate;
import com.networknt.eventuate.common.EntityIdAndType;
import com.networknt.eventuate.common.Int128;
import com.networknt.eventuate.common.impl.*;
import com.networknt.eventuate.common.impl.sync.AggregateCrud;

import java.util.List;
import java.util.Optional;

public abstract class AbstractJdbcAggregateCrud implements AggregateCrud {
  protected EventuateJdbcAccess eventuateJdbcAccess;

  public AbstractJdbcAggregateCrud(EventuateJdbcAccess eventuateJdbcAccess) {
    this.eventuateJdbcAccess = eventuateJdbcAccess;
  }

  protected void publish(PublishableEvents publishableEvents) {
    // Do nothing
  }

  @Override
  public EntityIdVersionAndEventIds save(String aggregateType, List<EventTypeAndData> events, Optional<AggregateCrudSaveOptions> options) {
    SaveUpdateResult result = eventuateJdbcAccess.save(aggregateType, events, options);
    publish(result.getPublishableEvents());
    return result.getEntityIdVersionAndEventIds();
  }

  @Override
  public <T extends Aggregate<T>> LoadedEvents find(String aggregateType, String entityId, Optional<AggregateCrudFindOptions> findOptions) {
    return eventuateJdbcAccess.find(aggregateType, entityId, findOptions);
  }

  @Override
  public EntityIdVersionAndEventIds update(EntityIdAndType entityIdAndType, Int128 entityVersion, List<EventTypeAndData> events, Optional<AggregateCrudUpdateOptions> updateOptions) {
    SaveUpdateResult result = eventuateJdbcAccess.update(entityIdAndType, entityVersion, events, updateOptions);
    publish(result.getPublishableEvents());
    return result.getEntityIdVersionAndEventIds();
  }
}
