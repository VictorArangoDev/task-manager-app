import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'iniciales',
})
export class InicialesPipe implements PipeTransform {

   transform(value: string | null | undefined): string {
    if (!value) return '';


    return value
      .split(' ')
      .map(palabra => palabra.charAt(0).toUpperCase())
      .join('')
      .substring(0, 2);
  }

}
