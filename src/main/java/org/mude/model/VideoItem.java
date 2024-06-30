package org.mude.model;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.mude.model.generics.GenericItem;
@Data
@Table(name = "video")
@Entity
public class VideoItem extends GenericItem {
}