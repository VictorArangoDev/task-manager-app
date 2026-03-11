export interface ProjectResponse {
  id:               number;
  name:             string;
  description:      string;
  state:            boolean;
  startDate:        Date;
  endDate:          null;
  deadline:         Date;
  createdAt:        Date;
  updatedAt:        Date;
  stateProjectTask: StateProjectTask;
}

export interface StateProjectTask {
  id:        number;
  name:      string;
  createdAt: Date;
  updatedAt: Date;
}
