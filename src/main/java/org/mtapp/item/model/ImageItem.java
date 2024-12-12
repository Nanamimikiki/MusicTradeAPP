package org.mtapp.item.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.mtapp.item.model.base.BaseItem;

@Data
@Entity
@Table(name = "images")
public class ImageItem extends BaseItem {
}
