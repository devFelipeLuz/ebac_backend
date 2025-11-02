package br.com.feluz.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TB_TASK")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
    @SequenceGenerator(name = "task_seq", sequenceName = "sq_task", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_status", nullable = false, length = 50)
    private Task.Status status;

    public enum Status {
        PENDENTE, CONCLUIDO;

        public static Task.Status getByName(String value) {
            for (Task.Status status : Task.Status.values()) {
                if (status.name().equals(value)) {
                    return status;
                }
            }
            return null;
        }
    }
}
