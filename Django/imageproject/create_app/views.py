
from .serializers import CreateSerializer
#from .serializers import postSerializer

from rest_framework.generics import (CreateAPIView)
from rest_framework.views import APIView
from rest_framework.response import Response
from create_app.models import Post

class PostCreateAPIView(CreateAPIView):
	serializer_class = CreateSerializer
	queryset = Post.objects.all()


	def post(self, request):
		return Response("123")

# class postList(CreateAPIView):
# 	def get(self, request):
# 		allpost = Post.objects.all()
# 		serializer = postSerializer(allpost, many=True)
# 		return Response(serializer.data)

# 	def post(self):
# 		pass
