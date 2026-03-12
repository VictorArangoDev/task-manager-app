import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'statusClasses',
})
export class StatusClassesPipe implements PipeTransform {

  transform(state: string): string {
    const classMap: Record<string, string> = {
      'TO-DO': 'bg-purple-100 text-purple-700 dark:bg-purple-900/30 dark:text-purple-400',
      'DONE': 'bg-green-100 text-green-700 dark:bg-green-900/30 dark:text-green-400',
      'CANCELL': 'bg-red-100 text-red-700 dark:bg-red-900/30 dark:text-red-400',
      'IN-PROGRESS': 'bg-blue-100 text-blue-700 dark:bg-blue-900/30 dark:text-blue-400'
    };

    return classMap[state] || 'bg-gray-100 text-gray-700 dark:bg-gray-900/30 dark:text-gray-400';
  }
}
