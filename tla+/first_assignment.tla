--------------------------- MODULE first_assignment ---------------------------

EXTENDS TLC, Integers, Sequences
CONSTANTS ExplorersMaxQueueSize, FileReadersMaxQueueSize, DataManMaxQueueSize, IntVisMaxQueueSize

(*--algorithm folder_explorer
variables explorersQueue = <<"dir">>, fileReadersQueue = <<>>, dataManQueue = <<>>, intVisQueue = <<>>;
define
    TypeInvariant == /\ Len(explorersQueue) <= ExplorersMaxQueueSize 
                     /\ Len(fileReadersQueue) <= FileReadersMaxQueueSize 
                     /\ Len(dataManQueue) <= DataManMaxQueueSize 
                     /\ Len(intVisQueue) <= IntVisMaxQueueSize
end define;

process explorer \in {"exp1", "exp2"}
variables dir = "none", file = "none";
begin Explore:
    while TRUE do
        e_take:
            await explorersQueue /= <<>>;
            dir := Head(explorersQueue);
            explorersQueue := Tail(explorersQueue);
        e_explore:
            print dir;
        e_produce:
            file := "Expl Prod Item";
            dir := "dir";
        e_put:
            await Len(fileReadersQueue) < FileReadersMaxQueueSize;
            fileReadersQueue := Append(fileReadersQueue, file);
        e_put_dir:
            await Len(explorersQueue) < ExplorersMaxQueueSize;
            explorersQueue := Append(explorersQueue, dir);
    end while;
end process;


process fileReader \in {"fr1", "fr2"}
variable item = "none";
begin ReadFile:
    while TRUE do
        f_take:
            await fileReadersQueue /= <<>>;
            item := Head(fileReadersQueue);
            fileReadersQueue := Tail(fileReadersQueue);
        f_read:
            print item;
        f_produce:
            item := "FR Prod Item";
        f_put:
            await Len(dataManQueue) < DataManMaxQueueSize;
            dataManQueue := Append(dataManQueue, item);
    end while;
end process;

process dataManager \in {"dm1", "dm2"}
variable item = "none";
begin ProcData:
    while TRUE do
        dm_take:
            await dataManQueue /= <<>>;
            item := Head(dataManQueue);
            dataManQueue := Tail(dataManQueue);
        dm_store:
            print item;
        dm_produce:
            item := "DM Prod Item";
        dm_put:
            await Len(intVisQueue) < IntVisMaxQueueSize;
            intVisQueue := Append(intVisQueue, item);
    end while;
end process;

process interface \in {"interface"}
variable item = "none"
begin ShowData:
    while TRUE do
        i_take:
            await intVisQueue /= <<>>;
            item := Head(intVisQueue);
            intVisQueue := Tail(intVisQueue);
        i_show:
            print item;
    end while;
end process;
end algorithm;*)
\* BEGIN TRANSLATION (chksum(pcal) = "9083d8ae" /\ chksum(tla) = "eb729562")
\* Process variable item of process fileReader at line 39 col 10 changed to item_
\* Process variable item of process dataManager at line 57 col 10 changed to item_d
VARIABLES explorersQueue, fileReadersQueue, dataManQueue, intVisQueue, pc

(* define statement *)
TypeInvariant == /\ Len(explorersQueue) <= ExplorersMaxQueueSize
                 /\ Len(fileReadersQueue) <= FileReadersMaxQueueSize
                 /\ Len(dataManQueue) <= DataManMaxQueueSize
                 /\ Len(intVisQueue) <= IntVisMaxQueueSize

VARIABLES dir, file, item_, item_d, item

vars == << explorersQueue, fileReadersQueue, dataManQueue, intVisQueue, pc, 
           dir, file, item_, item_d, item >>

ProcSet == ({"exp1", "exp2"}) \cup ({"fr1", "fr2"}) \cup ({"dm1", "dm2"}) \cup ({"interface"})

Init == (* Global variables *)
        /\ explorersQueue = <<"dir">>
        /\ fileReadersQueue = <<>>
        /\ dataManQueue = <<>>
        /\ intVisQueue = <<>>
        (* Process explorer *)
        /\ dir = [self \in {"exp1", "exp2"} |-> "none"]
        /\ file = [self \in {"exp1", "exp2"} |-> "none"]
        (* Process fileReader *)
        /\ item_ = [self \in {"fr1", "fr2"} |-> "none"]
        (* Process dataManager *)
        /\ item_d = [self \in {"dm1", "dm2"} |-> "none"]
        (* Process interface *)
        /\ item = [self \in {"interface"} |-> "none"]
        /\ pc = [self \in ProcSet |-> CASE self \in {"exp1", "exp2"} -> "Explore"
                                        [] self \in {"fr1", "fr2"} -> "ReadFile"
                                        [] self \in {"dm1", "dm2"} -> "ProcData"
                                        [] self \in {"interface"} -> "ShowData"]

Explore(self) == /\ pc[self] = "Explore"
                 /\ pc' = [pc EXCEPT ![self] = "e_take"]
                 /\ UNCHANGED << explorersQueue, fileReadersQueue, 
                                 dataManQueue, intVisQueue, dir, file, item_, 
                                 item_d, item >>

e_take(self) == /\ pc[self] = "e_take"
                /\ explorersQueue /= <<>>
                /\ dir' = [dir EXCEPT ![self] = Head(explorersQueue)]
                /\ explorersQueue' = Tail(explorersQueue)
                /\ pc' = [pc EXCEPT ![self] = "e_explore"]
                /\ UNCHANGED << fileReadersQueue, dataManQueue, intVisQueue, 
                                file, item_, item_d, item >>

e_explore(self) == /\ pc[self] = "e_explore"
                   /\ PrintT(dir[self])
                   /\ pc' = [pc EXCEPT ![self] = "e_produce"]
                   /\ UNCHANGED << explorersQueue, fileReadersQueue, 
                                   dataManQueue, intVisQueue, dir, file, item_, 
                                   item_d, item >>

e_produce(self) == /\ pc[self] = "e_produce"
                   /\ file' = [file EXCEPT ![self] = "Expl Prod Item"]
                   /\ dir' = [dir EXCEPT ![self] = "dir"]
                   /\ pc' = [pc EXCEPT ![self] = "e_put"]
                   /\ UNCHANGED << explorersQueue, fileReadersQueue, 
                                   dataManQueue, intVisQueue, item_, item_d, 
                                   item >>

e_put(self) == /\ pc[self] = "e_put"
               /\ Len(fileReadersQueue) < FileReadersMaxQueueSize
               /\ fileReadersQueue' = Append(fileReadersQueue, file[self])
               /\ pc' = [pc EXCEPT ![self] = "e_put_dir"]
               /\ UNCHANGED << explorersQueue, dataManQueue, intVisQueue, dir, 
                               file, item_, item_d, item >>

e_put_dir(self) == /\ pc[self] = "e_put_dir"
                   /\ Len(explorersQueue) < ExplorersMaxQueueSize
                   /\ explorersQueue' = Append(explorersQueue, dir[self])
                   /\ pc' = [pc EXCEPT ![self] = "Explore"]
                   /\ UNCHANGED << fileReadersQueue, dataManQueue, intVisQueue, 
                                   dir, file, item_, item_d, item >>

explorer(self) == Explore(self) \/ e_take(self) \/ e_explore(self)
                     \/ e_produce(self) \/ e_put(self) \/ e_put_dir(self)

ReadFile(self) == /\ pc[self] = "ReadFile"
                  /\ pc' = [pc EXCEPT ![self] = "f_take"]
                  /\ UNCHANGED << explorersQueue, fileReadersQueue, 
                                  dataManQueue, intVisQueue, dir, file, item_, 
                                  item_d, item >>

f_take(self) == /\ pc[self] = "f_take"
                /\ fileReadersQueue /= <<>>
                /\ item_' = [item_ EXCEPT ![self] = Head(fileReadersQueue)]
                /\ fileReadersQueue' = Tail(fileReadersQueue)
                /\ pc' = [pc EXCEPT ![self] = "f_read"]
                /\ UNCHANGED << explorersQueue, dataManQueue, intVisQueue, dir, 
                                file, item_d, item >>

f_read(self) == /\ pc[self] = "f_read"
                /\ PrintT(item_[self])
                /\ pc' = [pc EXCEPT ![self] = "f_produce"]
                /\ UNCHANGED << explorersQueue, fileReadersQueue, dataManQueue, 
                                intVisQueue, dir, file, item_, item_d, item >>

f_produce(self) == /\ pc[self] = "f_produce"
                   /\ item_' = [item_ EXCEPT ![self] = "FR Prod Item"]
                   /\ pc' = [pc EXCEPT ![self] = "f_put"]
                   /\ UNCHANGED << explorersQueue, fileReadersQueue, 
                                   dataManQueue, intVisQueue, dir, file, 
                                   item_d, item >>

f_put(self) == /\ pc[self] = "f_put"
               /\ Len(dataManQueue) < DataManMaxQueueSize
               /\ dataManQueue' = Append(dataManQueue, item_[self])
               /\ pc' = [pc EXCEPT ![self] = "ReadFile"]
               /\ UNCHANGED << explorersQueue, fileReadersQueue, intVisQueue, 
                               dir, file, item_, item_d, item >>

fileReader(self) == ReadFile(self) \/ f_take(self) \/ f_read(self)
                       \/ f_produce(self) \/ f_put(self)

ProcData(self) == /\ pc[self] = "ProcData"
                  /\ pc' = [pc EXCEPT ![self] = "dm_take"]
                  /\ UNCHANGED << explorersQueue, fileReadersQueue, 
                                  dataManQueue, intVisQueue, dir, file, item_, 
                                  item_d, item >>

dm_take(self) == /\ pc[self] = "dm_take"
                 /\ dataManQueue /= <<>>
                 /\ item_d' = [item_d EXCEPT ![self] = Head(dataManQueue)]
                 /\ dataManQueue' = Tail(dataManQueue)
                 /\ pc' = [pc EXCEPT ![self] = "dm_store"]
                 /\ UNCHANGED << explorersQueue, fileReadersQueue, intVisQueue, 
                                 dir, file, item_, item >>

dm_store(self) == /\ pc[self] = "dm_store"
                  /\ PrintT(item_d[self])
                  /\ pc' = [pc EXCEPT ![self] = "dm_produce"]
                  /\ UNCHANGED << explorersQueue, fileReadersQueue, 
                                  dataManQueue, intVisQueue, dir, file, item_, 
                                  item_d, item >>

dm_produce(self) == /\ pc[self] = "dm_produce"
                    /\ item_d' = [item_d EXCEPT ![self] = "DM Prod Item"]
                    /\ pc' = [pc EXCEPT ![self] = "dm_put"]
                    /\ UNCHANGED << explorersQueue, fileReadersQueue, 
                                    dataManQueue, intVisQueue, dir, file, 
                                    item_, item >>

dm_put(self) == /\ pc[self] = "dm_put"
                /\ Len(intVisQueue) < IntVisMaxQueueSize
                /\ intVisQueue' = Append(intVisQueue, item_d[self])
                /\ pc' = [pc EXCEPT ![self] = "ProcData"]
                /\ UNCHANGED << explorersQueue, fileReadersQueue, dataManQueue, 
                                dir, file, item_, item_d, item >>

dataManager(self) == ProcData(self) \/ dm_take(self) \/ dm_store(self)
                        \/ dm_produce(self) \/ dm_put(self)

ShowData(self) == /\ pc[self] = "ShowData"
                  /\ pc' = [pc EXCEPT ![self] = "i_take"]
                  /\ UNCHANGED << explorersQueue, fileReadersQueue, 
                                  dataManQueue, intVisQueue, dir, file, item_, 
                                  item_d, item >>

i_take(self) == /\ pc[self] = "i_take"
                /\ intVisQueue /= <<>>
                /\ item' = [item EXCEPT ![self] = Head(intVisQueue)]
                /\ intVisQueue' = Tail(intVisQueue)
                /\ pc' = [pc EXCEPT ![self] = "i_show"]
                /\ UNCHANGED << explorersQueue, fileReadersQueue, dataManQueue, 
                                dir, file, item_, item_d >>

i_show(self) == /\ pc[self] = "i_show"
                /\ PrintT(item[self])
                /\ pc' = [pc EXCEPT ![self] = "ShowData"]
                /\ UNCHANGED << explorersQueue, fileReadersQueue, dataManQueue, 
                                intVisQueue, dir, file, item_, item_d, item >>

interface(self) == ShowData(self) \/ i_take(self) \/ i_show(self)

Next == (\E self \in {"exp1", "exp2"}: explorer(self))
           \/ (\E self \in {"fr1", "fr2"}: fileReader(self))
           \/ (\E self \in {"dm1", "dm2"}: dataManager(self))
           \/ (\E self \in {"interface"}: interface(self))

Spec == Init /\ [][Next]_vars

\* END TRANSLATION 
=============================================================================
