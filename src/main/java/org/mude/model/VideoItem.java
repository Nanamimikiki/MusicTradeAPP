package org.mude.model;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.mude.model.generics.GenericItem;
@Data
@Table(name = "video")
@DiscriminatorColumn(name = "VIDEO", discriminatorType = DiscriminatorType.STRING)
@Entity
public class VideoItem extends GenericItem {

}
