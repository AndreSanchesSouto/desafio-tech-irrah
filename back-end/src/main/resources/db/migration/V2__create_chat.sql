CREATE TABLE chats(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_adminer_id UUID NOT NULL,
    user_common_id UUID NOT NULL,
    FOREIGN KEY (user_adminer_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (user_common_id) REFERENCES users(id) ON DELETE CASCADE
)