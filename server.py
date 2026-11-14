from http.server import BaseHTTPRequestHandler, HTTPServer

class C2Handler(BaseHTTPRequestHandler):
    def do_GET(self):
        if self.path == '/ping':
            print("\n[+] Устройство на связи! Пинг получен.")
            self.send_response(200)
            self.send_header('Content-type', 'application/json')
            self.end_headers()
            self.wfile.write(b'{"status":"ok"}')
        else:
            self.send_response(404)
            self.end_headers()

    def log_message(self, format, *args):
        # Отключаем лишний мусор в логах
        pass

if __name__ == '__main__':
    port = 8000
    server = HTTPServer(('0.0.0.0', port), C2Handler)
    print(f"Сервер запущен на порту {port}. Жду пинги...")
    server.serve_forever()
