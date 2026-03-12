import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'priority',
})
export class PriorityPipe implements PipeTransform {
  
 transform(priority: string | null | undefined): string {
    if (!priority) return 'bg-gray-100 text-gray-700 dark:bg-gray-900/30 dark:text-gray-400';

    const priorityClasses: Record<string, string> = {
      'LOW': 'bg-gray-100 text-gray-700 dark:bg-gray-900/30 dark:text-gray-400',
      'MEDIUM': 'bg-yellow-100 text-yellow-700 dark:bg-yellow-900/30 dark:text-yellow-400',
      'HIGH': 'bg-red-100 text-red-700 dark:bg-red-900/30 dark:text-red-400'
    };

    return priorityClasses[priority.toUpperCase()] || 'bg-gray-100 text-gray-700 dark:bg-gray-900/30 dark:text-gray-400';
  }

}
