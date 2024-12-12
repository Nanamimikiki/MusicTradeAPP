package org.mtapp.item.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.mtapp.item.model.base.BaseItem;

import java.util.Date;

@Data
@Entity
@Table(name = "audio")
public class AudioItem extends BaseItem {
}
