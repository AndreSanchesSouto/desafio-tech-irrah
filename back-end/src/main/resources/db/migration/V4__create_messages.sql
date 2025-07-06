CREATE TABLE messages(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    chat_id UUID NOT NULL,
    user_sender_id UUID NOT NULL,
    user_receiver_id UUID NOT NULL,
    text VARCHAR(1000) NOT NULL,
    price NUMERIC(4,2) NOT NULL,
    priority_level VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE,
    FOREIGN KEY (user_sender_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (user_receiver_id) REFERENCES users(id) ON DELETE CASCADE

)