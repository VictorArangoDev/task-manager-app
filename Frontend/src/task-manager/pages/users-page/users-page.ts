import { Component, computed, inject } from '@angular/core';
import { OptionsComponent } from "../../../components/options-component/options-component";
import { UserService } from '../services/user.service';
import { rxResource } from '@angular/core/rxjs-interop';
import { InicialesPipe } from '../../../app/iniciales-pipe';

@Component({
  selector: 'users-page',
  imports: [OptionsComponent, InicialesPipe],
  templateUrl: './users-page.html',
})
export class UsersPage {

  totalUsers = computed(() => this.userResource.value()?.length || 0);

  userService = inject(UserService);


  userResource = rxResource({
    params: () => ({}),
    stream: () => {
      return this.userService.getUsers();
    }
  });






}
