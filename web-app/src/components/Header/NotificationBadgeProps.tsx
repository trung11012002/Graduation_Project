// src/components/NotificationBadge.tsx
import React from 'react';

interface NotificationBadgeProps {
    hasUnreadNotifications: boolean;
}

const NotificationBadge: React.FC<NotificationBadgeProps> = ({ hasUnreadNotifications }) => {
    if (!hasUnreadNotifications) return null;

    return (
        <span
            style={{
                position: 'absolute',
                top: '-5px',
                right: '-5px',
                width: '10px',
                height: '10px',
                backgroundColor: 'red',
                borderRadius: '50%',
                display: 'block',
            }}
        />
    );
};

export default NotificationBadge;
