import { Component, Input, OnChanges, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { Departman } from 'src/app/models/departman';
import { Status } from 'src/app/models/status';
import { Student } from 'src/app/models/student';
import { StudentService } from 'src/app/services/student.service';
import { StudentDialogComponent } from '../dialogs/student-dialog/student-dialog.component';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.css']
})
export class StudentComponent implements OnInit, OnChanges, OnDestroy {

  displayedColumns = ["id","brojIndeksa","ime","prezime","status","departman","actions"];
dataSource: MatTableDataSource<Student>;
@Input() selektovaniDepartman: Departman;
@ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
@ViewChild(MatSort,{static: false}) sort: MatSort;
subscription: Subscription;

  constructor(private studentService:StudentService,private dialog:MatDialog) { }

  ngOnInit(): void {
   // this.loadData();

  }

  ngOnChanges(): void{
    if(this.selektovaniDepartman.id){
      this.loadData();
    }
  }

  ngOnDestroy(): void{
    this.subscription.unsubscribe();
  }

  loadData():void{
    this.studentService.getStudentiZaDepartman(this.selektovaniDepartman.id)
    .subscribe(
      data => {
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.sort = this.sort;
   this.dataSource.paginator = this.paginator;

        this.dataSource.filterPredicate = (data, filter: string) => {
          const accumulator = (currentTerm, key) => {
            return key === 'status' ? currentTerm + data.status.naziv : currentTerm + data[key];
          };
          const dataStr = Object.keys(data).reduce(accumulator, '').toLowerCase();
          const transformedFilter = filter.trim().toLowerCase();
          return dataStr.indexOf(transformedFilter) !== -1;
        };

         // sortiranje po nazivu ugnježdenog objekta
        this.dataSource.sortingDataAccessor = (data, property) => {
          switch (property) {
            case 'status': return data.status.naziv.toLocaleLowerCase();
            default: return data[property];
          }
        };
      }
    ),
    (error:Error) => {
      console.log(error.name + ' ' + error.message);
     }
  }

public openDialog(flag: number, id?:number, ime?: string, prezime?:string, brojIndeksa?: string, status?: Status,departman?:Departman){
const dialogRef = this.dialog.open(StudentDialogComponent,{data: {id,ime,prezime, brojIndeksa,status,departman}});
dialogRef.componentInstance.flag = flag;
if(flag === 1){
  dialogRef.componentInstance.data.departman = this.selektovaniDepartman;
}
dialogRef.afterClosed()
.subscribe(
  result => {
    if(result === 1){
      this.loadData();
    }
  }
)

}
applyFilter(filterValue: string){
  filterValue=filterValue.trim();
  filterValue = filterValue.toLocaleLowerCase();
  this.dataSource.filter = filterValue;
}
}


