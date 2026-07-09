export interface ViolationDetailsResponse {

  violationId: number;

  violation: string;

  nameOfViolator: string;

  applicableProcedure: string;

  referringAuthority: string;

  isMeeting: boolean;

  notes: string;

  date: string;

}
