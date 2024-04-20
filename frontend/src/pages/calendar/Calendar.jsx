// import { LocalizationProvider, StaticDatePicker } from "@mui/x-date-pickers";
// import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
// import React, { useState } from "react";
// import moment from "moment";

// export const getDaysInMonth = (monthMoment) => {
//   const monthCopy = monthMoment.clone();
//   monthCopy.startOf("month");

//   let days = [];

//   while (monthCopy.month() === monthMoment.month()) {
//     days.push(monthCopy.clone());
//     monthCopy.add(1, "days");
//   }
//   return days;
// };

// export const segmentIntoWeeks = (dayMoments) => {
//   let weeks = [];
//   let currentWeek = [];

//   for (let day of dayMoments) {
//     currentWeek.push(day.clone());

//     if (day.format("dddd") === "Saturday") {
//       weeks.push(currentWeek);
//       currentWeek = [];
//     }
//   }

//   if (currentWeek.length > 0) {
//     weeks.push(currentWeek);
//   }

//   return weeks;
// };

// const padWeekFront = (week, padWidth = null) => {
//   return [...Array(7 - week.length).fill(padWidth), ...week];
// };

// const padWeekBack = (week, padWidth = null) => {
//   return [...week, ...Array(7 - week.length).fill(padWidth)];
// };

// const daysOfTheWeek = [
//   "Sunday",
//   "Monday",
//   "Tuesday",
//   "Wednesday",
//   "Thursday",
//   "Friday",
//   "Saturday",
// ];

// const Calendar = () => {
//   const currentMonthMoment = moment();
//   const weeks = segmentIntoWeeks(getDaysInMonth(currentMonthMoment));

//   return (
//     <div>
//       <h1>{currentMonthMoment.format("MMMM YYYY")}</h1>
//       <table>
//         <thead>
//           <tr>
//             {" "}
//             {daysOfTheWeek.map((day) => (
//               <th key={day}>{day}</th>
//             ))}
//           </tr>
//         </thead>
//         <tbody>
//           {weeks.map((week, i) => {
//             const displayWeek =
//               i === 0
//                 ? padWeekFront(week)
//                 : i === weeks.length - 1
//                 ? padWeekBack(week)
//                 : week;
//             return (
//               <tr key={i}>
//                 {displayWeek.map((dayMoment, j) =>
//                   dayMoment ? (
//                     <td key={dayMoment.format("D")}>{dayMoment.format("D")}</td>
//                   ) : (
//                     <td key={`${i}${j}`}></td>
//                   )
//                 )}
//               </tr>
//             );
//           })}
//         </tbody>
//       </table>
//     </div>
//   );
// };
// export default Calendar;
import { Calendar as BigCalendar, momentLocalizer } from "react-big-calendar";
import moment from "moment";

const localizer = momentLocalizer(moment);

const Calendar = (props) => {
  return <BigCalendar {...props} localizer={localizer} />;
};
export default Calendar;
