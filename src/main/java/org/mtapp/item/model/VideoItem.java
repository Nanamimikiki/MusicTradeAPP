package org.mtapp.item.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.mtapp.item.model.base.BaseItem;

@Data
@Table(name = "video")
@Entity
public class VideoItem extends BaseItem {
}