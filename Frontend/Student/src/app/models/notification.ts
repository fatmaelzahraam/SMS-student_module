export interface NotificationItem {
  id?: number;
  title: string;
  body: string;        
  type: string;
  priority: string;
  sentAt: string;      
  isRead?: boolean;
}