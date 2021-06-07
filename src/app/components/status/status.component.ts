import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { Status } from 'src/app/models/status';
import { StatusService } from 'src/app/services/status.service';
import { StatusDialogComponent } from '../dialogs/status-dialog/status-dialog.component';

@Component({
  selector: 'app-status',
  templateUrl: './status.component.html',
  styleUrls: ['./status.component.css']
})
export class StatusComponent implements OnInit, OnDestroy {

  displayedColumns = ["id","naziv","oznaka","actions"]; //niz kolona koje zelim da imam u tabeli
dataSource: MatTableDataSource<Status>;
subscription: Subscription;
@ViewChild(MatPaginator, {static: false}) paginator: MatPaginator
@ViewChild(MatSort,{static: false}) sort: MatSort

  constructor(private statusService: StatusService, private dialog:MatDialog) { }

  ngOnInit(): void {
    this.loadData();
  }

  ngOnDestroy(): void {
this.subscription.unsubscribe();
  }

public loadData(){
 this.subscription = this.statusService.getAllStatus()
  .subscribe(data => {
   // console.log(data);
   this.dataSource = new MatTableDataSource(data);
   this.dataSource.sort = this.sort;
   this.dataSource.paginator = this.paginator;
  }),
  (error: Error) => {
    console.log(error.name + ' ' + error.message);
  }
}

public openDialog(flag:number, id?:number, naziv?:string, oznaka?: string){
const dialogRef = this.dialog.open(StatusDialogComponent, {data: {id,naziv,oznaka}});
dialogRef.componentInstance.flag = flag;
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
