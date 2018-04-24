package thaumcraft.common.entities.construct.golem.tasks;

import java.util.concurrent.*;
import thaumcraft.api.golems.tasks.*;
import net.minecraft.entity.*;
import java.util.*;
import thaumcraft.common.entities.construct.golem.*;
import thaumcraft.common.entities.construct.golem.seals.*;
import thaumcraft.api.golems.*;
import thaumcraft.api.golems.seals.*;
import net.minecraft.world.*;

public class TaskHandler
{
    static final int TASK_LIMIT = 1000;
    public static ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Task>> tasks;
    
    public static void addTask(final int dim, final Task ticket) {
        if (!TaskHandler.tasks.containsKey(dim)) {
            TaskHandler.tasks.put(dim, new ConcurrentHashMap<Integer, Task>());
        }
        final ConcurrentHashMap<Integer, Task> dc = TaskHandler.tasks.get(dim);
        if (dc.size() > 1000) {
            try {
                final Iterator<Task> i = dc.values().iterator();
                if (i.hasNext()) {
                    i.next();
                    i.remove();
                }
            }
            catch (Exception ex) {}
        }
        dc.put(ticket.getId(), ticket);
    }
    
    public static Task getTask(final int dim, final int id) {
        return getTasks(dim).get(id);
    }
    
    public static ConcurrentHashMap<Integer, Task> getTasks(final int dim) {
        if (!TaskHandler.tasks.containsKey(dim)) {
            TaskHandler.tasks.put(dim, new ConcurrentHashMap<Integer, Task>());
        }
        return TaskHandler.tasks.get(dim);
    }
    
    public static ArrayList<Task> getBlockTasksSorted(final int dim, final UUID uuid, final Entity golem) {
        final ConcurrentHashMap<Integer, Task> tickets = getTasks(dim);
        final ArrayList<Task> out = new ArrayList<Task>();
    Label_0025:
        for (final Task ticket : tickets.values()) {
            if (!ticket.isReserved()) {
                if (ticket.getType() != 0) {
                    continue;
                }
                if (uuid != null && ticket.getGolemUUID() != null && !uuid.equals(ticket.getGolemUUID())) {
                    continue;
                }
                if (out.size() == 0) {
                    out.add(ticket);
                }
                else {
                    double d = ticket.getPos().func_177957_d(golem.field_70165_t, golem.field_70163_u, golem.field_70161_v);
                    d -= ticket.getPriority() * 256;
                    for (int a = 0; a < out.size(); ++a) {
                        double d2 = out.get(a).getPos().func_177957_d(golem.field_70165_t, golem.field_70163_u, golem.field_70161_v);
                        d2 -= out.get(a).getPriority() * 256;
                        if (d < d2) {
                            out.add(a, ticket);
                            continue Label_0025;
                        }
                    }
                    out.add(ticket);
                }
            }
        }
        return out;
    }
    
    public static ArrayList<Task> getEntityTasksSorted(final int dim, final UUID uuid, final Entity golem) {
        final ConcurrentHashMap<Integer, Task> tickets = getTasks(dim);
        final ArrayList<Task> out = new ArrayList<Task>();
    Label_0025:
        for (final Task ticket : tickets.values()) {
            if (!ticket.isReserved()) {
                if (ticket.getType() != 1) {
                    continue;
                }
                if (uuid != null && ticket.getGolemUUID() != null && !uuid.equals(ticket.getGolemUUID())) {
                    continue;
                }
                if (out.size() == 0) {
                    out.add(ticket);
                }
                else {
                    double d = ticket.getPos().func_177957_d(golem.field_70165_t, golem.field_70163_u, golem.field_70161_v);
                    d -= ticket.getPriority() * 256;
                    for (int a = 0; a < out.size(); ++a) {
                        double d2 = out.get(a).getPos().func_177957_d(golem.field_70165_t, golem.field_70163_u, golem.field_70161_v);
                        d2 -= out.get(a).getPriority() * 256;
                        if (d < d2) {
                            out.add(a, ticket);
                            continue Label_0025;
                        }
                    }
                    out.add(ticket);
                }
            }
        }
        return out;
    }
    
    public static void completeTask(final Task task, final EntityThaumcraftGolem golem) {
        if (task.isCompleted() || task.isSuspended()) {
            return;
        }
        final ISealEntity se = SealHandler.getSealEntity(golem.field_70170_p.field_73011_w.getDimension(), task.getSealPos());
        if (se != null) {
            task.setCompletion(se.getSeal().onTaskCompletion(golem.field_70170_p, golem, task));
        }
        else {
            task.setCompletion(true);
        }
    }
    
    public static void clearSuspendedOrExpiredTasks(final World world) {
        final ConcurrentHashMap<Integer, Task> tickets = getTasks(world.field_73011_w.getDimension());
        final ConcurrentHashMap<Integer, Task> temp = new ConcurrentHashMap<Integer, Task>();
        for (final Task ticket : tickets.values()) {
            if (!ticket.isSuspended() && ticket.getLifespan() > 0L) {
                ticket.setLifespan((short)(ticket.getLifespan() - 1L));
                temp.put(ticket.getId(), ticket);
            }
            else {
                final ISealEntity sEnt = SealHandler.getSealEntity(world.field_73011_w.getDimension(), ticket.getSealPos());
                if (sEnt == null) {
                    continue;
                }
                sEnt.getSeal().onTaskSuspension(world, ticket);
            }
        }
        TaskHandler.tasks.put(world.field_73011_w.getDimension(), temp);
    }
    
    static {
        TaskHandler.tasks = new ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Task>>();
    }
}
