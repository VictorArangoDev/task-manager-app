export interface Tasks {
   id:               number;
  name:             string;
  description:      string;
  startDate:        null;
  endDate:          null;
  deadline:         Date;
  stateProjectTask: Priority;
  priority:         Priority;
  createdAt:        Date;
  updatedAt:        Date;
}



export interface Priority {
  id:        number;
  name:      string;
  createdAt: Date;
  updatedAt: Date;
}
