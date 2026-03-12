
export interface UserInterface {
  id:           number;
  username:     string;
  name:         string;
  document:     string;
  email:        string;
  password:     string;
  image:        null;
  sessionToken: null;
  state:        boolean;
  createdAt:    Date;
  updatedAt:    Date;
  role:         Role;
}

export interface Role {
  id:          number;
  name:        string;
  description: string;
  createdAt:   Date;
  updatedAt:   Date;
  state:       boolean;
}
