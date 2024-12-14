import "./index.css";

interface PropsAvatar {
  width?: string;
  url?: string;
}

const Avatar: React.FC<PropsAvatar> = ({ url, width }) => {
  const avatarUrl =
    url ||
    "https://cdn-icons-png.flaticon.com/128/2202/2202112.png";

  const avatarWidth = width || "50px";
  return (
    <div
      className="avatar_circle"
      style={{ width: avatarWidth, height: avatarWidth }}
    >
      <img src={avatarUrl} alt="" />
    </div>
  );
};

export default Avatar;