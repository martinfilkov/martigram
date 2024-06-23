package com.udemy.martigram.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "follow",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"follower_id", "followed_id"})
        }
)
public class GramFollow {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "follower_id")
        private GramUser follower;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "followed_id")
        private GramUser followed;
}
